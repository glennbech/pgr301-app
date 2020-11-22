import { useAuth0 } from '@auth0/auth0-react';
import React, { useState } from 'react';
import axios from 'axios';

function CreateWish() {
	const [title, setTitle] = useState('');
	const [url, setUrl] = useState('');
	const [description, setDescription] = useState('');
	const { isAuthenticated, getAccessTokenSilently } = useAuth0();

	const onSubmit = (e: React.FormEvent) => {
		e.preventDefault();
		getAccessTokenSilently().then((token) => {
			const options = {
				headers: {
					Authorization: `Bearer ${token}`,
				},
			};
			axios
				.post('/wishes', { title, url, description }, options)
				.then((response) => {
					setTitle('');
					setUrl('');
					setDescription('');
				});
		});
	};

	const changeHandler = (
		e: React.FormEvent<HTMLInputElement> | React.FormEvent<HTMLTextAreaElement>
	) => {
		switch (e.currentTarget.name) {
			case 'title':
				setTitle(e.currentTarget.value);
				break;
			case 'url':
				setUrl(e.currentTarget.value);
				break;
			case 'description':
				setDescription(e.currentTarget.value);
				break;
		}
	};

	return isAuthenticated ? (
		<div className="create-wish">
			<h1>What do you wish for?</h1>
			<form onSubmit={onSubmit}>
				<div className="input-group">
					<label>Title:</label>
					<input
						className="input"
						name="title"
						value={title}
						onChange={changeHandler}
						placeholder="puppy"
						type="text"
					/>
				</div>
				<div className="input-group">
					<label>Url:</label>
					<input
						className="input"
						placeholder="url"
						name="url"
						value={url}
						onChange={changeHandler}
						type="text"
					/>
				</div>
				<div className="input-group">
					<label>Description:</label>
					<textarea
						className="input"
						placeholder="dogoo"
						name="description"
						value={description}
                        onChange={changeHandler}
					/>
				</div>

				<div className="input-group">
					<button type="submit" className="input">
						Submit wish
					</button>
				</div>
			</form>
		</div>
	) : (
		<p>You need to be logged in to submit a new wish..</p>
	);
}

export default CreateWish;
