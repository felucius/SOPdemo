FROM payara/server-full:181


COPY ./target/*.war /opt/payara41/deployments/
