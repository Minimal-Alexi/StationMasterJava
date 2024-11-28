## Clock 0 (Initialization)

### State Table

| Phase | Ticket Check Queue | Ticket Check | Train Platform 1 | Train 1    | Train Platform 2 | Train 2    | Metro Platform  | Metro      |
|-------|--------------------|--------------|------------------|------------|------------------|------------|-----------------|------------|
|       | Empty              | Idle         | Empty            | Travelling | Empty            | Travelling | Empty           | Travelling |

### Event List
| Event | Time |
|-------|------|
| B1    | 30   |
| C1    | 240  |
| C2    | 300  |
| C3    | 120  |

### Work Complete
| Passenger Types   | Quantity |
|-------------------|----------|
| Train Passenger   | 0        |
| Metro Passenger   | 0        |

## Clock 30 (B1)
| Phase | Ticket Check Queue | Ticket Check | Train Platform 1 | Train 1    | Train Platform 2 | Train 2    | Metro Platform  | Metro      |
|-------|--------------------|--------------|------------------|------------|------------------|------------|-----------------|------------|
| B     | T1                 | Idle         | Empty            | Travelling | Empty            | Travelling | Empty           | Travelling |
| C     | Empty              | T1           | Empty            | Travelling | Empty            | Travelling | Empty           | Travelling |

### Event List
| Event | Time |
|-------|------|
| B1    | 60   |
| B2    | 75   |
| C1    | 240  |
| C2    | 300  |
| C3    | 120  |

### Work Complete
| Passenger Types   | Quantity |
|-------------------|----------|
| Train Passenger   | 0        |
| Metro Passenger   | 0        |

## Clock 60 (B1)
| Phase | Ticket Check Queue | Ticket Check | Train Platform 1 | Train 1    | Train Platform 2 | Train 2    | Metro Platform  | Metro      |
|-------|--------------------|--------------|------------------|------------|------------------|------------|-----------------|------------|
| B     | M1                 | T1           | Empty            | Travelling | Empty            | Travelling | Empty           | Travelling |

### Event List
| Event | Time |
|-------|------|
| B1    | 75   |
| B2    | 75   |
| C1    | 240  |
| C2    | 300  |
| C3    | 120  |

### Work Complete
| Passenger Types   | Quantity |
|-------------------|----------|
| Train Passenger   | 0        |
| Metro Passenger   | 0        |

## Clock 75 (B1)
| Phase | Ticket Check Queue | Ticket Check | Train Platform 1 | Train 1    | Train Platform 2 | Train 2    | Metro Platform  | Metro      |
|-------|--------------------|--------------|------------------|------------|------------------|------------|-----------------|------------|
| B     | M1                 | T1           | Empty            | Travelling | Empty            | Travelling | Empty           | Travelling |

### Event List
| Event | Time |
|-------|------|
| B1    | 75   |
| B2    | 75   |
| C1    | 240  |
| C2    | 300  |
| C3    | 120  |

### Work Complete
| Passenger Types   | Quantity |
|-------------------|----------|
| Train Passenger   | 0        |
| Metro Passenger   | 0        |