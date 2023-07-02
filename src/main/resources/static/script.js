const apiUrl = 'http://localhost:8080/api/bbs';

// 게시글 생성
const createPost = async (event) => {
    event.preventDefault();

    const title = document.getElementById('title-input').value;
    const content = document.getElementById('content-input').value;

    try {
        const response = await axios.post(`${apiUrl}/posts/create`, { "title" : title, "content" : content });
        console.log(response.data); // 생성된 게시글 정보 출력

        // 생성된 게시글을 화면에 추가
        const postElement = document.createElement('div');
        postElement.className = 'post';
        postElement.innerHTML = `
      <h3>${response.data.title}</h3>
      <p>${response.data.content}</p>
      <button class="delete-button" data-id="${response.data.id}">삭제</button>
      <button class="edit-button" data-id="${response.data.id}">수정</button>
    `;
        document.getElementById('posts-container').prepend(postElement);

        // 입력 필드 초기화
        document.getElementById('title-input').value = '';
        document.getElementById('content-input').value = '';
    } catch (error) {
        console.error(error);
    }
};

// 게시글 삭제
const deletePost = async (event) => {
    const postId = event.target.dataset.id;

    try {
        const response = await axios.delete(`${apiUrl}/${postId}`);
        console.log(response.data); // 삭제된 게시글 정보 출력

        // 삭제된 게시글을 화면에서 제거
        event.target.parentNode.remove();
    } catch (error) {
        console.error(error);
    }
};

// 게시글 수정
const editPost = async (event) => {
    const postId = event.target.dataset.id;
    const newTitle = prompt('새로운 제목:');
    const newContent = prompt('새로운 내용:');

    try {
        const response = await axios.patch(`${apiUrl}/${postId}`, { title: newTitle, content: newContent });
        console.log(response.data); // 수정된 게시글 정보 출력

        // 수정된 게시글 정보 갱신
        const postElement = event.target.parentNode;
        postElement.querySelector('h3').textContent = response.data.title;
        postElement.querySelector('p').textContent = response.data.content;
    } catch (error) {
        console.error(error);
    }
};

// 초기 게시글 조회
const getPosts = async () => {
    try {
        const response = await axios.get(`${apiUrl}/posts`);
        console.log(response.data); // 전체 게시글 정보 출력

        // 전체 게시글을 화면에 추가
        const postsContainer = document.getElementById('posts-container');
        response.data.forEach(post => {
            const postElement = document.createElement('div');
            postElement.className = 'post';
            postElement.innerHTML = `
        <h3>${post.title}</h3>
        <p>${post.content}</p>
        <button class="delete-button" data-id="${post.id}">삭제</button>
        <button class="edit-button" data-id="${post.id}">수정</button>
      `;
            postsContainer.appendChild(postElement);
        });
    } catch (error) {
        console.error(error);
    }
};

// 이벤트 핸들러 등록
document.getElementById('create-post-form').addEventListener('submit', createPost);
document.getElementById('posts-container').addEventListener('click', (event) => {
    if (event.target.classList.contains('delete-button')) {
        deletePost(event);
    } else if (event.target.classList.contains('edit-button')) {
        editPost(event);
    }
});

// 초기 게시글 조회
getPosts();
