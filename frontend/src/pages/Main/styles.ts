import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-color: #4d4acf;
	width: 70%;
	min-width: 1150px;
	margin: auto;
	padding-top: 7rem;
`;

export const Title = styled.h1`
	color: white;
	font-weight: 700;
	font-size: 3rem;
	margin: 3rem;
`;

export const DrawButton = styled.button`
	background-color: transparent;
	border: none;
	font-size: 10rem;
	cursor: pointer;
`;
