/* eslint-disable no-param-reassign */
import React, { useEffect, useState } from "react";
import { Doughnut } from "react-chartjs-2";
import "chart.js/auto";
import { API_TOKEN } from "Utils/localStorageKeys";
import { getSolvedCount } from "apis/statistics";
import { Container, Text } from "./styles";

const data = {
	labels: ["DP", "Greedy", "DFS", "Recursion", "브루트포스 ", "BFS"],
	datasets: [
		{
			data: [12, 19, 3, 5, 2, 3],
			backgroundColor: [
				"rgba(255, 99, 132, 0.2)",
				"rgba(54, 162, 235, 0.2)",
				"rgba(255, 206, 86, 0.2)",
				"rgba(75, 192, 192, 0.2)",
				"rgba(153, 102, 255, 0.2)",
				"rgba(255, 159, 64, 0.2)",
				"rgba(255, 1, 64, 0.2)",
				"rgba(75, 192, 1, 0.2)",
				"rgba(1, 192, 192, 0.2)",
				"rgba(1, 192, 1, 0.2)",
				"rgba(54, 162, 40, 0.2)",
				"rgba(1, 125, 255, 0.2)",
				"rgba(120, 159, 64, 0.2)",
				"rgba(255, 206, 222, 0.2)",
				"rgba(40, 100, 11, 0.2)",
				"rgba(40, 20, 20, 0.2)",
				"rgba(120, 255, 255, 0.2)",
				"rgba(255, 255, 1, 0.2)",
			],
			borderColor: [
				"rgba(255, 99, 132, 1)",
				"rgba(54, 162, 235, 1)",
				"rgba(255, 206, 86, 1)",
				"rgba(75, 192, 192, 1)",
				"rgba(153, 102, 255, 1)",
				"rgba(255, 159, 64, 1)",
				"rgba(255, 1, 64, 1)",
				"rgba(75, 192, 1, 1)",
				"rgba(1, 192, 192, 1)",
				"rgba(1, 192, 1, 1)",
				"rgba(54, 162, 40, 1)",
				"rgba(1, 125, 255, 1)",
				"rgba(120, 159, 64, 1)",
				"rgba(255, 206, 222, 1)",
				"rgba(40, 100, 11, 1)",
				"rgba(40, 20, 20, 1)",
				"rgba(120, 255, 255, 1)",
				"rgba(255, 255, 1, 1)",
			],
			borderWidth: 1,
		},
	],
};

function ProblemChart() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const labels: string[] = [];
	const counts: number[] = [];
	const [dataLoading, setDataLoading] = useState(false);

	useEffect(() => {
		(async () => {
			await getSolvedCount(apiToken)
				.then((res) => {
					res.data.response.forEach((data: any) => {
						labels.push(data.type);
						counts.push(data.count);
					});
					data.labels = labels;
					data.datasets[0].data = counts;
					setDataLoading(true);
				})
				.catch((err) => {
					console.log(err);
				});
		})();
	}, []);

	return (
		<Container>
			<Text style={{ marginBottom: "2rem" }}>차트로 보기</Text>
			{dataLoading ? <Doughnut data={data}>Chart</Doughnut> : null}
		</Container>
	);
}

export default ProblemChart;
