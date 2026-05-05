import pandas as pd
from sqlalchemy import create_engine
import os

# Параметры подключения берутся из переменных окружения
DB_URL = os.getenv("DATABASE_URL", "postgresql://user:password@localhost:5432/analytics")
TABLE_NAME = "shipments"

def export_to_csv():
    print(f"Exporting {TABLE_NAME} to CSV...")
    # try:
    #     engine = create_engine(os.getenv("DATABASE_URL"))
    #     query = "SELECT * FROM {TABLE_NAME}"
        
    #     # Читаем по 100к строк за раз
    #     chunk_size = 100000
    #     first_chunk = True
    #     rows_count = 0
        
    #     for chunk in pd.read_sql(query, engine, chunksize=chunk_size):
    #         # Дописываем в файл (mode='a'), заголовок пишем только один раз
    #         chunk.to_csv('/data/{TABLE_NAME}.csv', mode='a', index=False, header=first_chunk)
    #         first_chunk = False
    #         rows_count += len(chunk)
    #     print(f"Успешно экспортировано {rows_count} строк.")
    # except Exception as e:
    #     print(f"Ошибка: {e}")
    

if __name__ == "__main__":
    export_to_csv()
