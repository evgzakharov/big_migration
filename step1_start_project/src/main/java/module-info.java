module step_one.start.project.main {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires lombok;
    requires spring.context;
    requires spring.web;

    opens migration.simple.config;
    opens migration.simple.controller;
    opens migration.simple.repository;
    opens migration.simple.service;
}
