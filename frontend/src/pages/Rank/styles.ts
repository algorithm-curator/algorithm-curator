import styled from "@emotion/styled";

export const Container = styled.table`
	display: flex;
	flex-direction: column;
	margin: 7rem auto;
	background-color: #e0e0e0;
	width: 90%;
	max-width: 1600px;
	border-radius: 1rem;
	padding: 2rem;
`;

export const Title = styled.caption`
	font-size: 2rem;
	font-weight: 600;
	padding: 1rem;
	text-align: left;
`;

export const MenuWrapper = styled.tr`
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	border-radius: 1rem;
	background-color: white;
	padding: 1rem;
	border-bottom: 0.2rem solid #ffffff;
	margin-bottom: 2rem;
`;

export const Menu = styled.th`
	font-size: 1.3rem;
	font-weight: 500;
	text-align: center;
	width: 30%;
`;

export const MoreButton = styled.td`
	width: 10%;
	font-size: 1.2rem;
	padding: 0.5rem;
	background-color: #4d4acf;
	color: white;
	cursor: pointer;
	border: none;
	border-radius: 1rem;
	text-align: center;
`;

export const ToastMessage = styled.tr`
	color: red;
	font-size: 0.8rem;
	text-align: center;
`;
