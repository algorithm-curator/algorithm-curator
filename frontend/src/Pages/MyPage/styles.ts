import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	margin: 7rem auto;
	background-color: #e0e0e0;
	width: 90%;
	max-width: 800px;
	border-radius: 1rem;
	padding: 2rem;
`;

export const Title = styled.div`
	font-size: 2rem;
	font-weight: 600;
	padding: 1rem;
	text-align: left;
`;

export const ProfileWrapper = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	margin-top: 1rem;
`;

export const ProfileImage = styled.div<{ srcUrl: any }>`
	width: 200px;
	height: 200px;
	border-radius: 1rem;
	background-color: white;
	cursor: pointer;
	/* background-image: url(${(props) => props.srcUrl}); */
`;

export const ProfileEditSubmit = styled.button`
	margin-top: 3rem;
	border: none;
	background-color: rgba(0, 0, 0, 0.15);
	font-size: 1.2rem;
	font-weight: 500;
	width: fit-content;
	padding: 0.8rem 2rem;
	border-radius: 1rem;
	cursor: pointer;
`;

export const NicknameCheck = styled.button`
	margin: auto;
	padding: 0.5rem 1rem;
	border: none;
	background-color: rgba(255, 255, 255, 0.5);
	cursor: pointer;
`;
