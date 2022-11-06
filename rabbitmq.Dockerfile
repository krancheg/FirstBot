FROM rabbitmq:3.11.0-management
RUN rabbitmq-plugins enable --offline rabbitmq_amqp1_0