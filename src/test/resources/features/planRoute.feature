Feature: The user wants to plan a route

  Background:
    Given cookies accepted

  Rule: route must be calculated and shown

      Scenario: Plan a route with data table
        Given address A is "elsocim"
        And address B is "masodikcim"
        When button Plan is pressed
        Then calculated route is shown
          |elsocim        |masodikcim     |
          |"Kacsa utca"   |"Selyem utca"  |

