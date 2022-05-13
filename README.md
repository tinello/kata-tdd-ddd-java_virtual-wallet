# Java Standard project with DDD patterns covered with Unit Tests

Design the **Virtual Wallet** using Aggregate Roots, Entities and Value Objects and cover the uses cases with Unit Tests. At the Clean Architecture Manga you could learn the [DDD patterns](https://github.com/ivanpaulovich/clean-architecture-manga/wiki/Domain-Driven-Design-Patterns) and TDD at [TheThreeRulesOfTdd](http://butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd).

## :construction_worker: Use cases

This project was designed do cover the following use cases and requirements:

1. A Customer could register a new Checking Account using its personal details.
1. Allow a customer to deposit funds into an existing account.
1. Allow the customer to withdraw funds from an existing account.
1. Allow the customer to close a Checking Account only if the balance is zero.
1. Do not allow the Customer to Withdraw more than the existing funds.
1. Allow to get the account details.
1. Allow to get the customer details.

## The Domain Model

<img src="https://bitbucket.org/tinello/virtual-wallet/raw/4c2c851c586aedd4fcee653fc3dd70729a972827/doc/ddd-tdd-rich-domain-model.png" width="800" />

## Tech stuff

* JDK 8 or later
* JUnit
* Cucumber

## Kata

Build a Rich Domain from tests using DDD Building Blocks like Aggregate Roots, Entities and Value Objects with the help of `kata-initial` folder files.

### The expected Model has

* Customer and Checking Account Aggregate Roots.
* Credit and Debit Entities.
* Amount, Name and SSN Value Objects.

### * Create a Test Class for the Register use case.
### * Then begin with the domain implementation.
### * Refactor the code.
### * Add more tests and continue with the implementation.
### * Refactor and Repeat.