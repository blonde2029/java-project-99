FROM gradle:8.5-jdk20

WORKDIR /app

COPY . .

RUN gradle installDist

CMD ./build/install/app/bin/app

EXPOSE 8080