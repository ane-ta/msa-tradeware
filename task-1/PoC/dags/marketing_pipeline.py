from airflow import DAG
from airflow.operators.python import PythonOperator, BranchPythonOperator
from airflow.operators.empty import EmptyOperator
from airflow.operators.email import EmailOperator 

from datetime import datetime, timedelta
import random
import pandas as pd  #для csv

def send_failure_alert(context):
    print(f"--- ОТПРАВКА EMAIL: Ошибка в задаче {context['task_instance'].task_id}")
    # В реальном облаке тут вызывается спец. функция отправки

# 1. Настройка Retry и Failure callback
default_args = {
    'owner': 'marketing_dept',
    'start_date': datetime(2023, 1, 1),
    'retries': 3,                           # Повторы при ошибке
    'retry_delay': timedelta(minutes=1),    # Пауза между повторами
    'on_failure_callback': send_failure_alert,
    'email_on_failure': True,               # Уведомление при сбое
    'email_on_retry': False,
}

def analyze_data_func():
    # В данном прототипе данные считываются в DataFrame для наглядности. 
    # Для обработки реальных объемов, чтобы избежать перегрузки оперативной памяти оркестратора, архитектура предполагает: 
    # - использование Push-down подхода (вычисления на стороне БД)
    # - или передачу задачи в Spark-кластер
    file_path = '/opt/airflow/data/product-data.csv'
    
    df = pd.read_csv(file_path, usecols=['productAmount'])
    
    # среднее по колонке
    average_amount = df['productAmount'].mean()
    
    # количество строк
    count = len(df)
    
    print(f"--- Чтение завершено. Считано {count} строк.")
    print(f"--- Анализ завершен. Среднее кол-во продуктов: {average_amount}")
    return float(average_amount) * random.randint(1, 100) # Возвращаем число для развилки

def choose_path_func(ti): # task instance
    average = ti.xcom_pull(task_ids='analyze_data')

    path=''
    if average > 50:
        path = 'high_priority_path'
    else:
        path = 'standard_path'
    
    print(f"--- Ветвление. Получено среднее {average}. Выбран путь {path}.")
    
    return path

with DAG(
    'marketing_data_processing',
    default_args=default_args,
    schedule_interval='@daily',
    catchup=False
) as dag:

    # Шаг 1: Чтение данных
    analyze_data = PythonOperator(
        task_id='analyze_data',
        python_callable=analyze_data_func
    )

    # Шаг 2: Анализ и ветвление
    branching = BranchPythonOperator(
        task_id='branching',
        python_callable=choose_path_func
    )

    # Пути ветвления
    high_priority = EmptyOperator(task_id='high_priority_path')
    standard = EmptyOperator(task_id='standard_path')

    # Шаг 3: Уведомление об успехе
    send_success_email = EmailOperator(
        task_id='send_success_notification',
        to='marketing-manager@company.com',
        subject='Пайплайн маркетинга: Данные обработаны',
        html_content='<h3>Ура!</h3> <p>Анализ 1 млн записей завершен успешно.</p>',
        # Важно: чтобы он сработал после веток, ставим правило "хотя бы одна ветка прошла"
        trigger_rule='one_success' 
    )

    # Связи (Workflow)
    analyze_data >> branching >> [high_priority, standard] >> send_success_email
