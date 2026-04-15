# Production Dockerfile for Insurance Claims API
# Multi-stage build for optimal image size

# Stage 1: Build stage
FROM python:3.10-slim as builder

WORKDIR /app

# Install system dependencies for some python packages (e.g., psycopg2, bcrypt)
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir --user -r requirements.txt

# Stage 2: Runtime stage
FROM python:3.10-slim

WORKDIR /app

# Install runtime dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    libpq5 \
    && rm -rf /var/lib/apt/lists/*

# Copy installed packages from builder
COPY --from=builder /root/.local /root/.local
COPY --from=builder /app/requirements.txt .

# Make sure scripts in .local are usable
ENV PATH=/root/.local/bin:$PATH

# Copy application code
# We copy both backend and api directories to ensure imports work
COPY backend/ ./backend/
COPY api/ ./api/

# Set working directory to backend for uvicorn to find 'app'
WORKDIR /app/backend

# Default port for Render/Docker
ENV PORT=8000
EXPOSE 8000

# Run the application
CMD ["sh", "-c", "uvicorn app.main:app --host 0.0.0.0 --port ${PORT}"]
