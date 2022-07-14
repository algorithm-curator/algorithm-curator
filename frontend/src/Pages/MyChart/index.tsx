import React from "react";
import ProblemChart from "Components/MyChart/ProblemChart";
import UntilNowSolveCount from "Components/MyChart/UntilNowSolveCount";
import { Container } from "./styles";

function MyChart() {
	return (
		<Container>
			<div style={{ width: "50%" }}>
				<UntilNowSolveCount />
			</div>
			<div style={{ width: "50%" }}>
				<ProblemChart />
			</div>
		</Container>
	);
}

export default MyChart;
