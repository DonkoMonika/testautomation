Feature: The user wants to plan a route

  Background:
    Given page open
    And cookies accepted

  Rule: route must be calculated and shown

    Scenario Outline: Plan a route with data table
      Given address A is "<elsocim>"
      And address B is "<masodikcim>"
      When planning initiated
      Then calculated route is shown

      Examples:
        |elsocim        |masodikcim     |
        |Oktogon        |Budafok        |



