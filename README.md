# 🛍️ E-Commerce Microservices Platform

A cloud‑ready, scalable **eCommerce backend** built using **Spring Boot**, **Spring Cloud**, **Microservices Architecture**, and **Docker**. This project demonstrates real‑world distributed system design, API gateway routing, centralized configuration, and independent service deployments.

---

## 🚀 Overview

This project follows a production-style microservices architecture with multiple independent backend services:

- **Product Service**
- **User Service**
- **Authentication Service**
- **Cart Service**
- **Order Service**
- **Inventory Service**
- **API Gateway**
- **Service Registry (Eureka)**
- **Config Server**
- **Notification Service (optional)**

Each service is **independently deployable**, **scalable**, and fully **containerized** using Docker.

---

## 🧱 Architecture Diagram
# E-Commerce Microservices Architecture

```mermaid
flowchart TB
    subgraph Gateway Layer
        APIGW[API Gateway]
    end

    subgraph Discovery
        Eureka[(Service Discovery - Eureka)]
    end

    subgraph Config
        ConfigServer[(Central Config Server)]
    end

    subgraph Services
        Order[Order Service]
        Product[Product Service]
        User[User Service]
        Notification[Notification Service]
    end

    subgraph External Systems
        Keycloak[(Keycloak AuthN/AuthZ)]
        Mail[(SMTP/SMS Sender)]
        PaymentGateway[(Payment Provider)]
    end

    %% Connections
    APIGW -. REST .-> Order
    APIGW -. REST .-> Product
    APIGW -. REST .-> User
    APIGW -. REST .-> Notification

    Order -- REST --> Product
    Order -- REST --> User
    Order -- REST --> Notification
    Order -. REST .-> PaymentGateway

    Notification -- SMTP/SMS --> Mail

    User -- REST --> Keycloak

    APIGW -- Registers --> Eureka
    Order -- Registers --> Eureka
    Product -- Registers --> Eureka
    User -- Registers --> Eureka
    Notification -- Registers --> Eureka

    allservices((All Services)):::dummy
    allservices -. Fetch Config .-> ConfigServer

    classDef dummy fill:#eee,stroke:#eee;
```
## Details

- **API Gateway**: Entry point for all client requests, routes external traffic to services.
- **Eureka Service Discovery**: Enables services and API Gateway to dynamically find each other.
- **Config Server**: Central repository of configuration, fetched by all microservices at startup.
- **Order, Product, User, Notification Services**: Core Business Services, each with its own database.
    - All communications between services use REST (usually via Spring Feign/RestTemplate/WebClient).
    - Services register with Eureka for discovery.
- **External Integrations:**
    - **Keycloak** (SSO/Auth): User Service syncs & authenticates with Keycloak for authentication and RBAC.
    - **Payment Gateway**: Order Service calls external payment providers over REST.
    - **Email/SMS (Notification Service)**: Dispatches event-driven notifications via standard mail/SMS protocols.

## 🛠️ Tech Stack

- **Spring Boot**, **Spring Cloud**
- **Spring Security**, **JWT**
- **Docker / Buildpacks**
- **PostgreSQL / MySQL**  
- **Maven**

---

## ▶️ Running the Project

### Clone the project
```bash
git clone https://github.com/mayuri5401/<your-repo>.git
cd <your-repo>
            
