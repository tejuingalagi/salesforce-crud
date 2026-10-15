# Salesforce CRUD App

A web application for managing Salesforce records through OAuth 2.0 authentication with PKCE.

## Features

- Salesforce login and logout
- Manage Account, Opportunity, Lead, Contact, and Case records
- View, create, edit, and delete records
- Infinite scrolling with 20 records per batch
- React frontend integrated with Spring Boot
- Salesforce REST API integration

## Tech Stack

- Java 21
- Spring Boot 3.5.6
- React and Vite
- Axios
- Salesforce REST API
- OAuth 2.0 with PKCE

## Project Structure

- `src/` — Spring Boot backend
- `frontend/` — React frontend
- `pom.xml` — Maven configuration
- `Dockerfile` — Deployment configuration

## Run Locally

### Prerequisites

- Java 21
- Node.js and npm
- Maven
- Salesforce Developer Org and External Client App

### Configure Salesforce

Set these environment variables before starting the backend:

- `SALESFORCE_CLIENT_ID`
- `SALESFORCE_CLIENT_SECRET`
- `SALESFORCE_REDIRECT_URI` (optional; defaults to the localhost callback URL)

The local callback URL is:

`http://localhost:8080/oauth2/callback`

### Build the Frontend

From the project root, run:

```bash
cd frontend
npm ci
npm run build
```

Copy the contents of `frontend/dist/` into `src/main/resources/static/`.

### Start the Backend

From the project root, run:

```bash
mvn spring-boot:run
```

Open http://localhost:8080.

## Configuration

| Environment Variable | Purpose |
|---|---|
| `SALESFORCE_CLIENT_ID` | Salesforce consumer key |
| `SALESFORCE_CLIENT_SECRET` | Salesforce consumer secret |
| `SALESFORCE_REDIRECT_URI` | OAuth callback URL |
| `PORT` | Server port |

## Security

Keep Salesforce credentials in environment variables. Never commit credentials or `.env` files to GitHub.
