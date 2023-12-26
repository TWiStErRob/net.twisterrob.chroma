client.test("Application response", function() {
	const code = response.body["code"];
	client.assert(code === 1000, "Response code was " + code + " instead of 1000");
});
