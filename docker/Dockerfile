# MySQL için resmi Docker imajını kullan
FROM mysql:latest

# Ortam değişkenlerini ayarla
ENV MYSQL_ROOT_PASSWORD=plplpl123
ENV MYSQL_DATABASE=user-service
ENV MYSQL_USER=user-service
ENV MYSQL_PASSWORD=plplpl123

# MySQL yapılandırma dosyasını kopyala (isteğe bağlı)
# COPY my.cnf /etc/mysql/conf.d/my.cnf

# MySQL'in varsayılan portunu aç
EXPOSE 3306

# Konteyner başlatıldığında çalışacak komut
CMD ["mysqld"]