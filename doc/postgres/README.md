
Postgres Installation
====
####Step 1.add sources list file.

Create the file /etc/apt/sources.list.d/pgdg.list, and add a line for the repository

>[~] sudo vim /etc/apt/sources.list.d/pgdg.list

    deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main

Import the repository key

    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc| \ 
    sudo apt-key add -

>[~] sudo apt-get update
>[~] sudo apt-get install postgresql postgresql-contrib
>
####Step 2. create user
First connect/login as root

>[~] sudo su
>[~] su -postgres
>[~] psql
> psql (9.6.0)
> Type "help" for help.
>  postgres=#  CREATE USER dap PASSWORD 'dap';
>  postgres=#  CREATE SCHEMA mydb;
>  postgres=# GRANT ALL ON SCHEMA mydb TO dap;

####Step 3. remote setting
"/etc/postgresql/9.6/main/postgresql.conf"
>[~] sudo vim /etc/postgresql/9.6/main/postgresql.conf

    listen_addresses = '*'          # what IP address(es) to listen on;

>[~] sudo vim /etc/postgresql/9.6/main/pg_hba.conf 

    # IPv4 local connections:
    host    all             all             127.0.0.1/32            md5
    host    all             all             140.92.71.xx/32         md5
    host    all             all             140.92.71.xx/32         md5
    host    all             all             140.92.71.xxx/32        md5

>[~] sudo service postgresql restart
