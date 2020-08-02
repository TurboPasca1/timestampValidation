@validation
Feature: File name validation

  Scenario: Validate downloaded file name when it contains timestamp
    Given user is on my dummy website
    When user clicks on the download button
    Then the file has been downloaded to the default folder by "user1" user and file name starts with "hello" and ends with ".txt"