# ENGR 302: Final Project Report

**Group 15:** Monte Carlo Simulation Software Project
**Client:** Andre Geldenhuis
**Date:** TBC

## Project Objectives

The initial goal of this project was to create an extension to OpenRocket, an open-source rocket simulation software. This extension would allow the customer to run Monte Carlo simulations through OpenRocket. These simulations would take in mission control data (weather and rocket data) and a model rocket, and would run a number of simulations specified by the user. These simulations would be outputted graphically to the user, with options to save the output in various forms. This software would need to communicate with the Mission Control project software. The end goal was to also implement PID controller simulation for motor gimballing, and upwind rocket vectoring.

## Summary of Project Results

Over the course of this project, our group designed a program to extend OpenRocket, to run Monte Carlo rocket simulations. The software uses OpenRocket as a library, and can take in weather and rocket data, and can output the simulation data as a 3D graph, as 3D rocket flight paths, as points on a map, or as latitudes and longitudes in a CSV. The software also has a built-in API to communicate with Mission Control project.

## Original Scope and Delivered Scope

The original scope was to implement Monte Carlo Simulations as an extension of OpenRocket. These simulations should include mission control data and should integrate with the mission control teams' software. These simulations should also include PID controller simulation for motor gimballing and upwind rocket vectoring.

We have delivered all of the above, excluding PID control for motor gimballing (we do have PI for finn roll control however). We've also included a 3D graph of the output, a full API for mission control integration, and a street view map of the landing points.

## Original Expenditure and Actual Expenditure

The original budget was $0, as we were planning to use open-source software and development tools.

Our actual expenditure was also $0, as we have only used open-source software and development tools.

## Project Self-Assessment

The software we have delivered meets most of the initial project scope, excluding PID control simulation, and has some additional quality of life features that make the software much more user-friendly and polished. The software is flexible, with a GUI for stand-alone use, and an API for integration. We have also spent large amounts of time testing the software to ensure it is reasonably reliable and bug-free.

## Lessons Learned

During the project, we've learnt the importance of planning and writing documentation. The initial documentation provided us guidance throughout the project, and helped us to plan and manage scope, features, and time. We've learnt how to work using the Agile methodology, such as working in sprints and holding regular meetings and retrospectives. We've learnt how to use Gitlab's planning features and DevOps features, along with functions such as the pipeline. Finally, we've learnt how to work in a team effectively and how to communicate and plan effectively.

## Project Handover Plan

We will be presenting an overview of our project (and our individual contributions) in a 15 minute slot to the customer on the 21st of October, with a 5 minute Q&A. This will cover how to use the software, so the client can take on the project in its current state.

Future development could possibly cover PID controller simulations, especially for the motor gimballing.

All relevant documentation can be found on the GitLab, and should be packaged with the software so that it is available to anyone wanting to continue development.

