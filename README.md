
# Stream Log API

This project provides an API to schedule and log tasks with delay intervals. 

It uses Mutiny's reactive library to process tasks sequentially with support for delaying task start times. 

Additionally, the API prepares for "help" if specific conditions are met for upcoming tasks.

## Features

- **API endpoint** to schedule tasks and log them as they are processed.
- **Reactive** task processing using Mutiny for non-blocking, sequential execution.
- **Dynamic delay management** between tasks.
- **Helper preparation** based on task-specific conditions.

## Tech Stack

- Quarkus
- Java 21
- [Mutiny](https://smallrye.io/smallrye-mutiny/)
- [Lombok](https://projectlombok.org/)
- RESTful API with Jakarta RESTful Web Services (JAX-RS)

---

## Getting Started

### Prerequisites

- Java
- Maven 
- A REST client such as `curl` or Postman to test the API

### Running the Project

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yahyanouali/streamapi.git
   cd streamapi
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the project:**
   ```bash
   mvn quarkus:dev
   ```

The API should be available at `http://localhost:8080`.

---

## API Documentation

### Endpoint

#### Schedule Task Logging

- **URL**: `/log/schedule`
- **Method**: `POST`
- **Consumes**: `application/json`
- **Produces**: `text/event-stream`

This endpoint accepts a list of tasks, each with an `id`, `duration`, and a flag `needHelp`. The tasks will be processed sequentially with specified delays between each task. The `duration` represents the time each task should take, while the delay is determined dynamically.

---

### Request Payload

The request payload is a JSON array of `Task` objects:

| Parameter | Type    | Description                                               |
|-----------|---------|-----------------------------------------------------------|
| `id`      | String  | Unique identifier of the task                             |
| `duration`| Integer | Duration of the task in seconds                           |
| `needHelp`| Boolean | Flag indicating if help preparation is required for the task |

**Example**:
```json
[
  { "id": "1", "duration": 5, "needHelp": false },
  { "id": "2", "duration": 3, "needHelp": false },
  { "id": "3", "duration": 7, "needHelp": false },
  { "id": "4", "duration": 10, "needHelp": true },
  { "id": "5", "duration": 5, "needHelp": false },
  { "id": "6", "duration": 8, "needHelp": true }
]
```

### Example Usage

#### Schedule Tasks Using `curl`

To schedule tasks, send a `POST` request with your tasks to `/log/schedule`.

```bash
curl -N --location 'http://localhost:8080/log/schedule' \
--header 'Content-Type: application/json' \
--data '[
  { "id": "1", "duration": 5, "needHelp": false },
  { "id": "2", "duration": 3, "needHelp": false },
  { "id": "3", "duration": 7, "needHelp": false },
  { "id": "4", "duration": 10, "needHelp": true },
  { "id": "5", "duration": 5, "needHelp": false },
  { "id": "6", "duration": 8, "needHelp": true }
]'
```

Each task will be processed sequentially with logs indicating:

- Task start times and delays.
- Preparation for "help" when required by an upcoming task.

---

## Code Structure

The project follows a structured approach with classes dedicated to their responsibilities:

- **StreamLogApi**: RESTful API endpoint class that handles requests.
- **Task**: Simple POJO representing a task with `id`, `duration`, `needHelp`, and `delay`.
- **TaskProcessor**: Handles task scheduling and logging.
- **TaskScheduler**: Manages the delay introduction and help preparation logic.

---

## Dependencies

- **Mutiny**: Reactive library for asynchronous programming.
- **Lombok**: Simplifies Java code with annotations for boilerplate code reduction.

---

## Contributing

Feel free to open issues or submit pull requests for any improvements or feature requests.

---

## License

This project is open-source and available under the [MIT License](https://choosealicense.com/licenses/mit/).

---

## Author

Developed by [Yahya NOUALI](https://github.com/yahyanouali)
