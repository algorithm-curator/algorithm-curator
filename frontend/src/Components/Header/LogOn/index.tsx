import React from "react";
import { Container, Button } from "./styles";

function LogOn() {
	return (
		<Container>
			<Button to="/rank">Ranking</Button>
			<Button to="/problemList">Problem List</Button>
			<Button to="/mychart">Chart</Button>
			<Button to="/mypage">My Page</Button>
		</Container>
	);
}

export default LogOn;
