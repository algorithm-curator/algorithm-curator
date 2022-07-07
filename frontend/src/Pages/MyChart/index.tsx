import React from "react";
import ProblemChart from "Components/MyChart/ProblemChart";
import UntilNowSolveCount from "Components/MyChart/UntilNowSolveCount";
import { Container } from "./styles";

function MyChart() {
	return (
		<Container>
			<ProblemChart />
			<UntilNowSolveCount />
		</Container>
	);
}

export default MyChart;
