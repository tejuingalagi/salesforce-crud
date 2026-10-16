# Salesforce CRUD App

A web application for managing Salesforce records through OAuth 2.0 authentication with PKCE.

## Live Demo

- **Deployed Application:** https://salesforce-crud-jl8s.onrender.com
- **GitHub Repository:** https://github.com/tejuingalagi/salesforce-crud

Sign in with an authorized Salesforce account using OAuth 2.0 to view and manage Salesforce records.

**Note:** Create, update, and delete operations depend on Salesforce permissions, validation rules, and record relationships. Some records cannot be deleted while they are associated with other Salesforce records.

## Features

- Salesforce login and logout
- Manage Account, Opportunity, Lead, Contact, and Case records
- View, create, edit, and delete records
- Infinite scrolling with 20 records per batch
- React frontend integrated with Spring Boot
- Salesforce REST API integration
- OAuth 2.0 authentication with PKCE

## Tech Stack

- Java 21
- Spring Boot 3.5.6
- React and Vite
- Axios
- Salesforce REST API
- OAuth 2.0 with PKCE
- Maven
- Docker

## Project Structure

```text
salesforce-crud/
├── frontend/                 # React frontend
├── src/                      # Spring Boot backend
│   └── main/
│       ├── java/              # Backend source code
│       └── resources/
│           └── application.properties
├── pom.xml                   # Maven configuration
├── Dockerfile                # Docker deployment configuration
├── .dockerignore
└── README.md
```

## Prerequisites

- Java 21
- Node.js and npm
- Maven
- Salesforce Developer Org
- Salesforce External Client App configured for OAuth 2.0

## Configuration

Set the following environment variables before running the application:

| Environment Variable | Purpose |
|---|---|
| `SALESFORCE_CLIENT_ID` | Salesforce consumer key |
| `SALESFORCE_CLIENT_SECRET` | Salesforce consumer secret |
| `SALESFORCE_REDIRECT_URI` | OAuth callback URL |
| `PORT` | Server port; defaults to `8080` |

For local development, the OAuth callback URL is:

```text
http://localhost:8080/oauth2/callback
```

For deployment, configure the following environment variables in your hosting platform (Render):

- `SALESFORCE_CLIENT_ID` — Your Salesforce consumer key.
- `SALESFORCE_CLIENT_SECRET` — Your Salesforce consumer secret.
- `SALESFORCE_REDIRECT_URI` — `https://salesforce-crud-jl8s.onrender.com/oauth2/callback`

Register the exact same callback URL in your Salesforce External Client App OAuth settings.

For local development, use `http://localhost:8080/oauth2/callback` as the callback URL.

Keep credentials in environment variables. Never commit secrets or `.env` files to GitHub.
## Run Locally

### 1. Configure Salesforce credentials

Set `SALESFORCE_CLIENT_ID`, `SALESFORCE_CLIENT_SECRET`, and, if needed, `SALESFORCE_REDIRECT_URI`.

### 2. Build the React frontend

From the project root, run:

```bash
cd frontend
npm ci
npm run build
```

Copy the generated contents of `frontend/dist/` into `src/main/resources/static/` for the manual Spring Boot build.

### 3. Start the Spring Boot backend

Return to the project root and run:

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Docker Deployment

The root `Dockerfile` builds the React frontend, packages the generated frontend files with the Spring Boot application, and runs the application in a Java runtime container.

Build the Docker image from the project root:

```bash
docker build -t salesforce-crud .
```

Run the container with your Salesforce credentials configured as environment variables:

```bash
docker run -p 8080:8080 \
  -e SALESFORCE_CLIENT_ID=your_client_id \
  -e SALESFORCE_CLIENT_SECRET=your_client_secret \
  -e SALESFORCE_REDIRECT_URI=http://localhost:8080/oauth2/callback \
  salesforce-crud
```

Replace the placeholder credentials with your own values. Do not commit actual credentials to source control.

## Security

- Salesforce credentials are supplied through environment variables.
- OAuth 2.0 with PKCE is used for the authentication flow.
- Never commit client secrets or other credentials to GitHub.
