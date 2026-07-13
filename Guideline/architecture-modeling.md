# DBMS Architecture Modeling Guidelines

## Purpose

This document defines the architecture and modeling rules for generating UML Class Diagrams and Sequence Diagrams throughout the DBMS Architecture project.

The objective is to ensure that every diagram follows the same design philosophy, naming convention, architectural principles, and documentation style.

These rules must be applied consistently across every subsystem and feature.

---

# General Principles

## Design Before Drawing

The AI MUST NOT generate UML diagrams immediately.

Instead, it must complete the following analysis process first:

1. Understand the feature purpose.
2. Analyze responsibilities.
3. Identify all domain objects.
4. Classify each object.
5. Determine the Aggregate Root.
6. Identify relationships.
7. Validate the design against SOLID principles.
8. Generate the UML diagram.

Never skip any step.

---

# Class Diagram Rules

## Goal

High-Level Class Diagrams are intended to represent architecture rather than implementation.

The diagram should answer:

- What are the major classes?
- What are their responsibilities?
- How are they related?
- Which dependencies exist?

The diagram should NOT explain implementation details.

---

## Allowed Elements

The following element types are allowed:

- Entity
- Value Object
- Domain Service
- Factory
- Strategy
- Repository
- Interface
- Abstract Class (only if necessary)

---

## Forbidden Elements

High-Level Class Diagrams MUST NOT contain:

- Attributes
- Constructors
- Getter/Setter methods
- Private methods
- Protected methods
- Parameters
- Return types
- Visibility symbols (+, -, #)

Only class names and relationships should appear.

---

## Entity Rules

An Entity:

- Has identity.
- Owns its lifecycle.
- May act as an Aggregate Root.

Aggregate Roots should own Value Objects through Composition.

---

## Value Object Rules

A Value Object:

- Has no identity.
- Is immutable whenever possible.
- Cannot exist independently.
- Must belong to an Aggregate.

---

## Interface Rules

Interfaces should only exist when:

- Dependency Inversion Principle is required.
- Multiple implementations are expected.
- The abstraction provides architectural value.

Do not introduce interfaces unnecessarily.

Interfaces should use the prefix:

IExample

Examples:

- IMetadataFactory
- IMetadataValidator

---

## Abstract Class Rules

Create an Abstract Class ONLY when:

- Multiple implementations share common behavior.
- Shared logic naturally belongs in a base class.

Do NOT create abstract classes simply to satisfy OOP principles.

---

## Relationship Rules

Always choose the most appropriate UML relationship.

Preferred order:

- Composition
- Aggregation
- Association
- Dependency
- Realization
- Inheritance

Avoid using generic associations when a stronger relationship exists.

---

## Design Pattern Rules

Design Patterns should only be introduced when they solve a real architectural problem.

Examples:

- Factory
- Strategy
- State
- Observer
- Repository
- Specification

Never introduce a pattern simply because it exists.

---

# Dependency Rules

Dependencies should follow the Dependency Inversion Principle.

Managers and Services should depend on abstractions whenever possible.

Example:

MetadataManager

↓

IMetadataFactory

↓

MetadataFactory

NOT

MetadataManager

↓

MetadataFactory

---

# SOLID Validation

Before generating the final Class Diagram, verify:

- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

If any violation exists, propose improvements before generating the diagram.

---

# Naming Conventions

## Class

PascalCase

Examples:

DatabaseFile

MetadataManager

PageAllocator

---

## Interface

Prefix with I

Examples:

IMetadataFactory

IBufferPool

---

## Method

camelCase

Examples:

createDatabase()

allocatePage()

updateMetadata()

---

## Folder

Use kebab-case

Examples:

file-metadata

page-management

buffer-management

---

## Markdown Files

Examples:

README.md

high-level-class-diagram.md

create-database-file-sequence.md

---

# Sequence Diagram Rules

Each Sequence Diagram represents ONE use case only.

Examples:

- Create Database File
- Update Metadata
- Allocate Page
- Flush Buffer

Never combine multiple use cases into a single sequence diagram.

---

## Participants

Typical participants include:

- Actor
- Application Service
- Domain Service
- Aggregate Root
- Repository

Avoid including Value Objects unless they actively participate in message exchanges.

---

## Messages

Messages should represent business operations.

Examples:

createDatabase()

updateMetadata()

allocatePage()

validate()

Avoid using:

getXXX()

setXXX()

---

## Typical Flow

Actor

↓

Application Service

↓

Domain Service

↓

Aggregate Root

↓

Repository

↓

Return

---

# Documentation Workflow

Every feature should follow the same workflow.

Feature

↓

Purpose

↓

Responsibilities

↓

Domain Objects

↓

Classification

↓

Aggregate Root

↓

Relationships

↓

High-Level Class Diagram

↓

Sequence Diagrams

↓

Detailed Design

↓

Implementation

Never skip a phase.

---

# Final Checklist

Before generating any UML diagram, verify:

- Purpose is clearly defined.
- Responsibilities are complete.
- Domain Objects are identified.
- Aggregate Root is determined.
- Entity and Value Objects are classified.
- Services are identified.
- Interfaces are justified.
- Relationships are correct.
- SOLID principles are satisfied.
- Design Patterns are justified.
- Diagram remains at the intended abstraction level.

Only after all checks pass should the UML diagram be generated.

---

# Philosophy

This project follows the principle:

Design First.
Implementation Second.

Architecture should drive implementation, not the other way around.

Every UML diagram should communicate architectural intent rather than implementation details.