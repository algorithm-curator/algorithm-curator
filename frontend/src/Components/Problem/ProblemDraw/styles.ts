import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	justify-content: space-between;
	width: 70%;
	margin: 2rem auto;
	border-bottom: 0.2rem solid #ffffff;
`;

export const Title = styled.h2`
	font-size: 2rem;
	color: white;
`;

export const DrawWrapper = styled.div`
	display: flex;
	align-items: center;
`;

export const DrawText = styled.div`
	font-size: 1.5rem;
	color: #bdbdbd;
`;

export const DrawButton = styled.button`
	background-color: transparent;
	border: none;
	margin-left: 1rem;
	font-size: 1.5rem;
	cursor: pointer;
`;
