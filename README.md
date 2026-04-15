# Explainable Offline Health Insurance Claim Adjudication System

[![CD Pipeline - Docker Push](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/docker-push.yml/badge.svg)](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/docker-push.yml)
[![Setup for Render deployment](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/render-deploy.yml/badge.svg)](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/render-deploy.yml)
[![Fix tests](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/fix-tests.yml/badge.svg)](https://github.com/Rajath-MS-2006/InsuranceClaims/actions/workflows/fix-tests.yml)

## Research Novelty 🧠

This system represents a major step towards **Explainable AI (XAI)** in deterministic financial and medical domains. The novelty is grounded in three pillars:
1. **Fully Local & Offline Data Processing:** Ensures strict GDPR and HIPAA compliance. Instead of sending sensitive hospital bills to OpenAI/external APIs, the system utilizes local Hugging Face Transformers (`LayoutLM`, `DistilBERT`), guaranteeing zero data leakage.
2. **Semantic Deterministic Mapping:** We replace naive keyword search with a robust Semantic Ontology Mapper utilizing \`SentenceTransformers\`. This maps ambiguous raw medical line items ("Crocin 500mg") mathematically strictly to billing buckets ("Medicine"), bridging the gap between unstructured NLP and deterministic rule engines.
3. **Mathematical Trace Explainer:** Rather than using a LLM to "guess" an explanation for a rejected claim (which suffers from hallucination), the system generates step-by-step logic traces built directly from the deterministic rule bounds.

## Project Structure 📁
- `backend/`: FastAPI application containing the NLP, OCR, and Rule Engine.
- `frontend/`: React dashboard interfaces for Patient, Hospital, and Insurance.
- `data/`: Contains the foundational medical taxonomy graph (`ontology_base.json`).
- `local_models/`: Local cache directory for HuggingFace and SpaCy models.
- `eval/`: Automated Python scripts to evaluate F1 metrics for data extraction and system accuracy.

## Prerequisites 🛠️
1. [Python 3.10+](https://www.python.org/downloads/)
2. [Node.js & npm](https://nodejs.org/)
3. [Tesseract OCR](https://github.com/tesseract-ocr/tesseract) (Must be installed on Machine PATH).
4. Docker Desktop (For localized Postgres and MongoDB).

## Setup Instructions ⚙️

### 1. Database (Optional for Mock)
To start the persistent stores:
```bash
docker-compose up -d
```

### 2. Backend Initialization
```bash
cd backend
python -m venv venv
.\venv\Scripts\activate  # (Windows)
pip install -r requirements.txt

# Download AI Models for Offline processing (will cache ~1-2GB)
python download_models.py

# Start Backend API
uvicorn app.main:app --reload
```

### 3. Frontend Initialization
```bash
cd frontend
npm install
npm run dev
```

## Running Evaluation
You can run simulated evaluation accuracy tests using the `eval/` toolkit to see rule matching and extraction logic scoring:
```bash
python eval/metric_runner.py
```

## CI/CD Workflow 🚀

This project is configured with GitHub Actions for automated testing and deployment:

1.  **CD Pipeline - Docker Push**: Builds a production-ready Docker image and pushes it to Docker Hub on every push to `main`.
2.  **Render Deployment**: Automatically builds the frontend and triggers a backend redeploy to Render on every push to `main`.
3.  **Comprehensive Testing**: Runs unit tests for both FastAPI (backend) and React/Vitest (frontend) on every pull request and push, generating coverage reports.

### Configuration

To enable these workflows, add the following secrets to your GitHub repository:
- `DOCKER_USERNAME`: Docker Hub username.
- `DOCKER_PASSWORD`: Docker Hub password/token.
- `RENDER_API_KEY`: Render API Key.
- `RENDER_SERVICE_ID`: Render Service ID.
