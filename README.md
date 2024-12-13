# StationMaster Simulation User manual

Welcome to the StationMaster Simulation program! This tool allows you to
simulate the operations of multiple transportation hubs: rail and metro
stations, and passenger flow dynamics.

# Getting Started

Launch the Program: Open the StationMaster Simulation application on
your computer by running the file in src\main\java\Main.java in IntelliJ
IDEA

You will see the main interface where you can input simulation
parameters and adjust station settings.

# How to Use the Program

## Set Simulation Time

Days: Enter the number of days for the simulation.

Hours: Enter the number of hours (below 24).

Minutes: Enter the number of minutes (below 60).

Seconds: Enter the number of seconds (below 60).

These values define the total time span of the simulation.

## Enter a Seed Value

Input a numeric seed to initialize the random number generator. This
ensures repeatable simulation outcomes if the same seed is used.

## Adjust Station and Passenger Settings

Expand each of the available settings panels by clicking the dropdown
arrows:

- Rail Station 1 Settings, Rail Station 2 Settings, Metro Station
  Settings: Configure parameters specific to Rail Station 1, Rail
  Station 2, and Metro Station:

  - Travel Time: enter the time between 2 consecutive trains. Input
    values for Days, Hours, Minutes, and/or Seconds as required.

  - Loading Time: enter the time for the customer on the platform to
    enter the train, in minutes and seconds.

  - Capacity:

    - Set Capacity: Average Train Capacity: Input the average number of
      passengers a train can accommodate.

    - Variability in Train Capacity: Enter the range of variation in
      train capacity (e.g., 10 for +/- 10 passengers).

- Passenger Generator Settings: The passenger generator allows you to
  control how passengers are introduced into the system. Expand the
  settings to configure the following:

  - Generation Mean Ticket Check 1: Input the average time (in seconds)
    it takes passengers to pass through the first ticket check.

  - Generation Mean Ticket Check 2: Input the average time (in seconds)
    for the second ticket check process.

  - Train to Metro Passenger Ratio: Use the slider to set the percentage
    of passengers who will use trains to metro services. Example: A
    value of 30% means 30% of passengers will use trains

## Run the Simulation

Click the Run Simulation button to start the simulation with the
configured settings.

The simulation will execute for the specified duration, using the
configured seed and settings.

Note: you can leave any parameters as blank, and a predefined suitable
value will be automatically assigned in the simulation

During the simulation, you can increase and decrease the speed of the
simulation with the Speed Slider

## Check the result

After the simulation is completed, you can check the data for the Ticket
Check or Station by clicking on the corresponding tabs.

You can rerun the simulation with different parameters or quit the
program.

# Tips for Effective Use

Set Realistic Durations: For quick tests, use shorter durations (e.g.,
minutes or seconds).

Save Settings: Note down seed values and settings to replicate
simulations later.

Experiment: Modify one variable at a time to understand its impact on
the simulation results.
