FROM docker.elastic.co/beats/filebeat:7.17.28

ARG ROOT_PATH=/usr/share/filebeat
ARG LOGS_STREAM_PATH=/usr/share/filebeat/dockerlogs

# Copy our custom configuration file
COPY filebeat.yml ${ROOT_PATH}/filebeat.yml

# По умолчанию Filebeat может запускаться от менее привилегированного пользователя, 
# но для создания папок и изменения прав доступа в системных директориях нужны права суперпользователя.
USER root
# Сюда через docker-compose ты будешь «пробрасывать» (монтировать) папку с логами твоего Spring Batch приложения, чтобы Filebeat мог их прочитать.
# не бросает исключение, если папка уже есть, и создает всё дерево родителей.
RUN mkdir -p ${LOGS_STREAM_PATH} && \    
    chown -R root ${ROOT_PATH} && \
    chmod -R go-w ${ROOT_PATH}
# Filebeat очень строго относится к безопасности. 
# Он часто отказывается работать, если конфигурационные файлы принадлежат кому-то другому или имеют слишком широкие права на запись.

# Рекурсивно (-R) меняет владельца всех файлов в директории на пользователя root.
#  Снимает права на запись (-w) для группы (g) и остальных пользователей (o) рекурсивно.
