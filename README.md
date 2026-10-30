# Field & Form Shop

Field & Form is an online shop with a product catalog, customer accounts, shopping carts, checkout, order history, and a separate administration area.

## Requirements

- Docker Desktop with Docker Compose, or Java 17, Maven 3.9+, Node.js 22+, npm, and MySQL 8 for a local setup.

## Start with Docker Compose

1. Copy `.env.example` to `.env` and set unique database passwords, a JWT secret of at least 32 characters, and administrator credentials.
2. Start the services:

   ```powershell
   docker compose up -d --build
   ```

3. Open `http://localhost` or the port set by `APP_PORT`.

MySQL data is stored in the `mysql_data` volume. Flyway creates the schema and inserts the catalog when the database starts for the first time.

## Start services separately

Start the backend with MySQL running and the datasource configured in `backend/src/main/resources/application.yml` or environment variables:

```powershell
cd backend
mvn spring-boot:run
```

Then start the frontend in another terminal:

```powershell
cd frontend
npm install
npm start
```

The API is available at `http://localhost:8080/api`. Swagger UI is at `http://localhost:8080/swagger-ui.html`.

## Store pages

- Catalog: `/products` and `/products/:id`
- Customer account: `/register`, `/login`, and `/account`
- Cart and orders: `/cart`, `/checkout`, `/orders`, and `/orders/:id`
- Admin console: `/admin/dashboard`, `/admin/products`, `/admin/categories`, and `/admin/orders`

The admin account is created on startup when `ADMIN_EMAIL` and `ADMIN_PASSWORD` are set and the email is not already registered. Configure these values in `.env` for Docker Compose.

Orders start in `PENDING`. The `Pay` action is a local demonstration that changes the order status to `PAID`; no payment provider is connected. Cancelling a pending order restores its stock.

## Checks

Run backend checks with `mvn verify` from `backend`. Run frontend checks with `npm install`, then `npm test -- --watch=false --browsers=ChromeHeadless` and `npm run build` from `frontend`. GitHub Actions runs these checks and builds both Docker images.
