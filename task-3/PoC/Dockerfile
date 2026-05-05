FROM python:3.9-slim

WORKDIR /app

RUN pip install --no-cache-dir --default-timeout=1000  pandas sqlalchemy psycopg2-binary

COPY export.py .

# Создаем папку для сохранения CSV
RUN mkdir /data

CMD ["python", "export.py"]