import React from "react";
import ProblemChart from "components/myChart/ProblemChart";
import UntilNowSolveCount from "components/myChart/UntilNowSolveCount";
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
