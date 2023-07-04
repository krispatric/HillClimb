package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Project5 {

    public static void main(String[] args) {
        List<Double> brickWeights = generateWeights(100, 10.0, 30.0);
        List<String> allocation = hillClimbing(brickWeights);

        // Print the allocation
        System.out.println("Solution: " + allocation);
    }

    private static List<Double> generateWeights(int numWeights, double minWeight, double maxWeight) {
        List<Double> weights = new ArrayList<>();
        double step = (maxWeight - minWeight) / numWeights;

        for (int i = 0; i < numWeights; i++) {
            double weight = minWeight + i * step;
            weights.add(weight);
        }

        return weights;
    }

    private static List<String> hillClimbing(List<Double> brickWeights) {
        List<String> allocation = new ArrayList<>();

        // Assign weights randomly to Lorry A or Lorry B
        Random random = new Random();
        for (int i = 0; i < brickWeights.size(); i++) {
            if (random.nextBoolean()) {
                allocation.add("A");
            } else {
                allocation.add("B");
            }
        }

        double currentFitness = calculateFitness(brickWeights, allocation);
        double bestFitness = currentFitness;
        List<Double> fitnessValues = new ArrayList<>(); // Stores fitness values

        // Perform hill climbing iterations
        int numIterations = 1000;
        double perturbationFactor = 0.1;

        for (int iteration = 0; iteration < numIterations; iteration++) {
            List<String> mutatedAllocation = new ArrayList<>(allocation);

            // Randomly select a weight and switch its allocation between Lorry A and Lorry B
            int randomIndex = random.nextInt(brickWeights.size());
            String currentLorry = mutatedAllocation.get(randomIndex);
            String newLorry = (currentLorry.equals("A")) ? "B" : "A";
            mutatedAllocation.set(randomIndex, newLorry);

            double mutatedFitness = calculateFitness(brickWeights, mutatedAllocation);

            // If the mutated allocation has a lower fitness or equal fitness, accept the mutation
            if (mutatedFitness <= currentFitness) {
                allocation = mutatedAllocation;
                currentFitness = mutatedFitness;

                // Update the best fitness value
                if (mutatedFitness < bestFitness) {
                    bestFitness = mutatedFitness;
                }
            }

            fitnessValues.add(currentFitness); // Add fitness value to the list
        }



        // Print the fitness value for each iteration
        for (int i = 0; i < fitnessValues.size(); i++) {
            System.out.println("Iteration: " + (i + 1) + ", Fitness: " + fitnessValues.get(i));
        }
        // Print the best fitness value
        System.out.println("Best Fitness: " + bestFitness);
        return allocation;
    }

    private static double calculateFitness(List<Double> brickWeights, List<String> allocation) {
        double sumA = 0.0;
        double sumB = 0.0;

        for (int i = 0; i < brickWeights.size(); i++) {
            double weight = brickWeights.get(i);
            String lorry = allocation.get(i);

            if (lorry.equals("A")) {
                sumA += weight;
            } else {
                sumB += weight;
            }
        }

        return Math.abs(sumA - sumB);
    }
}
