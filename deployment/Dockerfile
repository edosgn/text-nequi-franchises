FROM openjdk:17-jdk
VOLUME /tmp

RUN apt-get update
RUN apt-get install -y curl

RUN addgroup --system appgroup
RUN adduser --system appuser
RUN usermod -a -G appgroup appuser
RUN chown -R appuser:appgroup /tmp

COPY applications/app-service/build/libs/Franchises.jar Franchises.jar
RUN sh -c 'touch /Franchises.jar'

ENV TZ=America/Bogota
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar Franchises.jar" ]


