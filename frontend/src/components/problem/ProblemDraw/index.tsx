/* eslint-disable @typescript-eslint/no-explicit-any */
import React from "react";
import { Container, Title, DrawText, DrawButton, DrawWrapper } from "./styles";

function ProblemDraw({ drawProblems }: any) {
	return (
		<Container>
			<Title>3문제</Title>
			<DrawWrapper>
				<DrawText>문제뽑기!</DrawText>
				<DrawButton onClick={drawProblems}>🔘</DrawButton>
			</DrawWrapper>
		</Container>
	);
}

export default ProblemDraw;
