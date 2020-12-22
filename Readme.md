### Развертывание minikube

Установка brew

    sudo yum groupinstall 'Development Tools' && sudo yum install curl file git

установить brew из под root нельзя, поэтому устанавливаем из под обычного пользователя hpc (пароль 1234567890)

    usermod -aG wheel hpc
    su hpc
    cd

    sh -c "$(curl -fsSL https://raw.githubusercontent.com/Linuxbrew/install/master/install.sh)"
    test -d ~/.linuxbrew && eval $(~/.linuxbrew/bin/brew shellenv)
    test -d /home/linuxbrew/.linuxbrew && eval $(/home/linuxbrew/.linuxbrew/bin/brew shellenv)
    test -r ~/.bash_profile && echo "eval \$($(brew --prefix)/bin/brew shellenv)" >>~/.bash_profile
    test -r ~/.bash_profile && echo "eval \$($(brew --prefix)/bin/brew shellenv)" >>~/.bashrc
    echo "eval \$($(brew --prefix)/bin/brew shellenv)" >>~/.profile

Установка minikube

    sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
    sudo chmod +x ./kubectl
    sudo mv ./kubectl /usr/local/bin/kubectl
    brew install minikube
    sudo usermod -aG docker $USER && newgrp docker

Запустить minikube

    minikube start --vm-driver=docker
    minikube dashboard &
    minikube ip

### Запуск в minikube

собрать, собрать образ
развернуть в minikube

    ./local-test.sh

Проверка доступности сервиса в minikube:

    GET http://{ip, полученный через minikube ip}:31007/person/get/all