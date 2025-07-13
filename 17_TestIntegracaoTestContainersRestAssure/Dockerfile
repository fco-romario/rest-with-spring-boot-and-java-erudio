FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


#Comandos no terminal

    #Limpar target: mvn clean
    #Criar jar no target: mvn package
    #Limpar e criar jar no target: mvn clean package -> equivalente ao que se faz pela idea do Intelij

    #Sem o .jar nao será possível incluir a aplicação dentro da Docker Image,
    #logo tbm não conseguirá subir um conteiner

    #Build da Docker image:
        #caso ja esteja dentro do diretório onde esta o .jar: docker build -t hello-docker:0.0.1-SNAPSHOT . (o nome do jar é hello-docker-0.0.1-SNAPSHOT)
        #caso esteja fora do direto onde esta o .jar: docker build -t hello-docker:0.0.1-SNAPSHOT ./nome da camada seguinte neste momento a linha
        # FROM eclipse-temurin:21-jdk serve para baixar (pull) uma imagem base pré-construída do Docker Hub (o repositório público de imagens Docker)
        #   e usá-la como base para o seu container.  (Docker Desktop tem que esta rodando)

    #para ver as images: docker images ou docker image ls

    #para subir conteinar a partir da image: docker run -p 80:80 -d hello-docker:0.0.1-SNAPSHOT
        #legenda: (docker run -p portdaMaquina:PortConteiner -d nomeColunaRepository:nomeColunaTag)
    #Quando você vê docker run -p 8080:80, significa que você está mapeando
     #uma porta do container Docker para uma porta no seu sistema host (máquina local).
     #Acessando http://localhost:8080 no seu navegador → tráfego é redirecionado para a porta 80 do container.
     #cuidado ao usar 8080:80, pode haver conflito com outro host. por isso optei por 80:80
     # agora é so abrir no navegardor o http://localhost:80/hello-docker

     #comandos uteis conteiner
        #docker ps -aq / docker container ls -> lista todos os containers (ativos e inativos) apenas com seus IDs.
        #docker stop força a parada dos containers em execução.
        #docker rm remove os containers parados.
        #para especificar um conteiner: docker conainer stop ID_AQUI  (ID do conteiner q vai ser removido.n precisa certodo o ID pode ser os dois primeiros)
        #inicia um conteinar inativo: docker container start ID_AQUI  (ID do conteiner q vai ser removido.n precisa certodo o ID pode ser os dois primeiros)
        #ver log do conteiner statico: docker logs ID_AQUI
        #ver log do conteiner em tempo real: docker logs -f ID_AQUI
        #mostra todos conteiner ativos ou n: docker container ls -a
        #pausa/despausar : docker container pause/unpause id_aqui
        #limpa conteiner inativo: docker container prune
        #encerra abrupitamente contrario do stop: docker container kill id_aqui
        #set parametro para sempre iniciar o container ao iniciar o docker (docker host): docker run -p 80:80 -d --restart=always hello-docker:0.0.1-SNAPSHOT (pode ser o contrario --restart=no pra nao iniciar)
        #tempo real de eventos que ocorrem no Docker: docker events
        #tempo real de eventos que ocorre em conteiner especifico: docker top
        #mostra estatistica do container: docker stats


     #comandos uteis image
        #remover image : doccker rmi mysql:8.0.29  (doccker rmi nomeColunaRepository:nomeColunaTag ou id)
        #criar tag latest: docker tag fcoromario/hello-docker:0.0.2-SNAPSHOT fcoromario/hello-docker:latest
        #buscar no Docker hub images: docker search fcoromario/hello-docker
            #busca apenas oficial: docker search mysql --filter is-official=true
        #exibe historico da image: docker image history fcoromario/hello-docker
        #inspecionar uma image: docker image inspect id_aqui


    #informaçoes do systema docker: docker system df

#docker hub
    #1. Criar uma Imagem Docker
    #2. Fazer Login no Docker Hub: login pelo console: docker login docker.io
    #3. Taggear a Imagem com Seu Usuário Docker Hub. Exemplo: docker tag minha-imagem:latest seuusuario/minha-imagem:latest
        #(exemplo: docker tag hello-docker:0.0.1-SNAPSHOT fcoromario/hello-docker:0.0.1-SNAPSHOT)
    #4. Enviar (Push) para o Docker Hub: docker push seuusuario/minha-imagem:latest
    #5. Verificar no Docker Hub.  Acesse seu perfil no Docker Hub e confirme se a imagem foi enviada.
    #6. pull da image do Docker Hub para a máquina: docker pull fcoromario/hello-docker (se tiver uma tag latest ele vai traser ele q seria a última)
        #o docker vai inicializar o spring dentro do container, porém no comando aterior nao seta a porta para a imagem, pois sua única função é baixar a imagem do Docker Hub para sua máquina local.
        # por isso precisamos mapear o conteiner a porta: docker run -p 8080:80 -d fcoromario/hello-docker (ex: porta 8080 do host para 80 do container)
        #pode ser ser o -d. O parâmetro -d no Docker (ou --detach) é usado para executar um container em segundo plano (modo "desanexado"). Isso significa que o container roda sem travar o terminal, liberando-o para outros comandos.
        #criar uma tag latest: ?
    #7
