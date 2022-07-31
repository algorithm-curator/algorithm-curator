import React from "react";
import { Container, Title, DrawText, DrawButton, DrawWrapper } from "./styles";

function ProblemDraw() {
	return (
		<Container>
			<Title>~ 문제</Title>
			<DrawWrapper>
				<DrawText>한번 더!</DrawText>
				<DrawButton>🔘</DrawButton>
			</DrawWrapper>
		</Container>
	);
}

export default ProblemDraw;
