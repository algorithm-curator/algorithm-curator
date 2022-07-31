import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	background-color: #c4c4c4;
	padding: 0.7rem;
	border-radius: 3rem;
	width: 70%;
	margin: auto;
`;

export const Title = styled.h2`
	margin-left: 2rem;
	font-size: 2rem;
	font-weight: 600;
`;

export const SolveLevelWrapper = styled.div`
	display: flex;
`;

export const SolveStatus = styled.select`
	background-color: #e0e0e0;
	border: none;
	option {
	}
`;

export const Level = styled.div`
	font-size: 1.5rem;
	background-color: #6fcf97;
	margin: 0 2rem;
	padding: 0.5rem 1rem;
	border-radius: 1rem;
`;
