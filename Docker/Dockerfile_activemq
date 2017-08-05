FROM dockerfile/java:oracle-java8

ADD spring-boot-activiti-restful-0.0.1.jar /opt/eip-smartkit/

EXPOSE 8082

WORKDIR /opt/eip-smartkit/

CMD ["java", "-Xmx1024m", "-jar", "spring-boot-activiti-restful-0.0.1.jar"]