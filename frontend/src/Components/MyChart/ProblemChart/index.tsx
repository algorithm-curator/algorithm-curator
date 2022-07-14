import React from "react";
import { Chart } from "react-chartjs-2";
import "chart.js/auto";
import { Container, Text } from "./styles";

const data = {
	labels: ["DP", "Greedy", "DFS", "Recursion", "브루트포스 ", "BFS"],
	datasets: [
		{
			data: [12, 19, 3, 5, 2, 3],
		},
	],
};

function ProblemChart() {
	return (
		<Container>
			<Text style={{ marginBottom: "2rem" }}>차트로 보기</Text>
			<Chart type="line" data={data} />
		</Container>
	);
}

export default ProblemChart;
