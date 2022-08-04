import React, { useEffect } from "react";
import axiosInstance from "Utils/axiosInstance";

function Test() {
	useEffect(() => {
		axiosInstance.get("/api/heartbeat").then((res) => {
			console.log(res);
		});
	}, []);
	return <div>Test</div>;
}

export default Test;
