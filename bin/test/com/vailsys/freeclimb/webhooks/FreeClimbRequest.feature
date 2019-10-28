Feature: FreeClimbRequest

	Scenario: Build a FreeClimbRequest object from JSON
		Given Given Some JSON create a FreeClimbRequest
		Then verify the FreeClimbRequest contents
