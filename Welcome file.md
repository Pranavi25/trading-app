

# Introduction  

- This project is an online stock trading simulation REST API which can perform certain tasks such as retrieve, update stock information of certain organisations.   
-  it an online stock trading simulation REST API which can blah..blah) and who can use this API (e.g. front-end developer, mobile developer, and traders can utilize this REST API)  - Briefly talk about technologies used in this project(e.g. It's a MicroService which is implemented with SpringBoot. PSQL database. IEX market data)   
     

# Quick Start  

- Prequiresites: Java, Docker, CentOS 7  - PSQL init  - git clone and mvn build  - Start Springboot app using a shell script   
   - describe env vars  - How to consume REST API? (Swagger screenshot and postman with OpenAPI Specification, e.g. [http://35.231.122.184:5000/v2/api-docs](http://35.231.122.184:5000/v2/api-docs)    
     

# REST API Usage  

## Swagger  

What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?  

## Quote Controller  

- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app  - briefly explain your endpoints in this controller   
   - GET `/quote/dailyList`: list all securities that are available to trading in this trading system   
   - PUT `/quote/iexMarketData`: Update all quotes from IEX which is an external market data source  

## Trader Controller  

- High-level description for trader controller(e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)  - briefly explain your endpoints in this controller  ##Order Controller  - High-level description for this controller.  - briefly explain your endpoints in this controller   
   - /order/MarketOrder: explain what is a market order, and how does your business logic work.  

## App controller  

- GET `/health` to make sure SpringBoot app is up and running  

## Optional(Dashboard controller)   
     

# Architecture  

- Draw a component diagram which contains controller, service, DAO, storage layers (you can mimic the diagram from the guide)  - briefly explain the following logic layers or components (3-5 sentences for each)   
   - Controller   
   - Service   
   - Dao   
   - SpringBoot: webservlet/TomCat and IoC   
   - PSQL and IEX   
     

# Improvements  

- at least 5 improvements  

# Introduction

-   Briefly explain what does this project do (e.g. it an online stock trading simulation REST API which can blah…blah) and who can use this API (e.g. front-end developer, mobile developer, and traders can utilize this REST API)
-   Briefly talk about technologies used in this project(e.g. It’s a MicroService which is implemented with SpringBoot. PSQL database. IEX market data)

# Quick Start

-   Prequiresites: Java, Docker, CentOS 7
-   PSQL init
-   git clone and mvn build
-   Start Springboot app using a shell script
    -   describe env vars
-   How to consume REST API? (Swagger screenshot and postman with OpenAPI Specification, e.g. [http://35.231.122.184:5000/v2/api-docs](http://35.231.122.184:5000/v2/api-docs)

# REST API Usage

## Swagger

What’s swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?

## Quote Controller

-   High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
-   briefly explain your endpoints in this controller
    -   GET `/quote/dailyList`: list all securities that are available to trading in this trading system
    -   PUT `/quote/iexMarketData`: Update all quotes from IEX which is an external market data source

## Trader Controller

-   High-level description for trader controller(e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
-   briefly explain your endpoints in this controller  
    ##Order Controller
-   High-level description for this controller.
-   briefly explain your endpoints in this controller
    -   /order/MarketOrder: explain what is a market order, and how does your business logic work.

## App controller

-   GET `/health` to make sure SpringBoot app is up and running

## Optional(Dashboard controller)

# Architecture

-   Draw a component diagram which contains controller, service, DAO, storage layers (you can mimic the diagram from the guide)
-   briefly explain the following logic layers or components (3-5 sentences for each)
    -   Controller
    -   Service
    -   Dao
    -   SpringBoot: webservlet/TomCat and IoC
    -   PSQL and IEX

# Improvements

-   at least 5 improvements

Markdown 2312 bytes 345 words 42 lines Ln 42, Col 25

HTML 1771 characters 309 words 38 paragraphs

Workspaces

3

Manage workspaces

List, rename, remove workspaces

----------

[](https://stackedit.io/app)

current

Main workspace

[](https://stackedit.io/app#providerId=githubWorkspace&owner=Pranavi25&repo=java-app&branch=master)

java-app

[](https://stackedit.io/app#providerId=githubWorkspace&owner=Pranavi25&repo=trading-app&branch=master)

trading-app

----------

Add a **CouchDB** workspace

Add a **GitHub** workspace

Add a **GitLab** workspace

Add a **Google Drive** workspace

# Introduction

# Quick Start

# REST API Usage

## Swagger

## Quote Controller

## Trader Controller

## App controller

## Optional(Dashboard controller)

# Architecture

# Improvements
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTc2NDkzNDcyMywyMTA3NTg3OTZdfQ==
-->