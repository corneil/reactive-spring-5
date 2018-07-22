package com.github.corneil.load_generator

import groovy.json.JsonSlurper
import org.springframework.web.reactive.function.client.WebClient

import java.util.concurrent.ConcurrentHashMap

class LoadGeneratorClient {
    WebClient client = WebClient.create('http://localhost:8080/extlast30days')

    int countResults() {

        def result = new JsonSlurper().parseText(client.get().exchange().block().bodyToMono(String).block())
        if (result instanceof Collection) {
            return result.size()
        }
        return result.length
    }

    long performRequest() {
        long startTime = System.nanoTime()
        client.get().exchange().block().bodyToMono(String).block()
        return System.nanoTime() - startTime
    }

    long invokeLoad(int iterations) {
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            totalTime += performRequest()
        }
        return totalTime / iterations
    }
}

static double average(Collection collection) {
    double total = 0.0
    collection.each {
        total += it as Long
    }
    return (total / (double) collection.size()) / 1000000.0
}

def totalPermutations = 1000
def permutations = [:]

def threadCount = []
[1, 2, 5, 10, 20, 50, 100, 200].each {
    if (it < totalPermutations) {
        permutations.put(it, totalPermutations / it)
        threadCount << it
    }
}
// warm up
println "Warming up"
def lg = new LoadGeneratorClient()
lg.invokeLoad(2)
def count = lg.countResults()
println "$count entries found"
long startTime = System.currentTimeMillis()
def totals = [:]
def avgs = [:]
threadCount.each { clients  ->
    int iterations = permutations[clients]
    def averages = new ConcurrentHashMap()
    def threads = []
    println "Starting $clients clients for $iterations iterations"
    for (int i = 0; i < clients; i++) {
        final int id = i
        threads << Thread.start {
            LoadGeneratorClient loadGenerator = new LoadGeneratorClient()
            long averageTime = loadGenerator.invokeLoad(iterations)
            averages.put(id, averageTime)
        }
    }
    println "Waiting for completion"
    threads.each {
        it.join()
    }
    double totalTime = (double) (System.currentTimeMillis() - startTime) / 1000.0
    double avg = average(averages.values())
    totals.put(clients, totalTime)
    avgs.put(clients, avg)
    println String.format('Client=%s, Iterations=%d, Average=%.1fms, Total time=%.1fs', clients, iterations, avg, totalTime)
}
println()
println 'Totals and Averages for Markdown'
println()
print '| Measure '
threadCount.each { clients ->
    print String.format('| %8s ', clients.toString())
}
println '|'
print '|---------'
threadCount.each {
    print '|---------:'
}
println '|'
print '| Totals  '
threadCount.each { client ->
    print String.format('| %8s ', String.format('%.1f', totals[client]))
}
println '|'
print '| Average '
threadCount.each { client ->
    print String.format('| %8s ', String.format('%.1f', avgs[client]))
}
println '|'
