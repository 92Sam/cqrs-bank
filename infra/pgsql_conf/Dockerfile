FROM postgres:13.5-bullseye
COPY docker-entrypoint.sh enable-extensions.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh /usr/local/bin/enable-extensions.sh
#CMD ["/bin/sh", "-c", "sleep 3600"]
