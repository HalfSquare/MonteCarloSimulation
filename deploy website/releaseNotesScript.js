const url = "https://gitlab.ecs.vuw.ac.nz/api/v4/projects/6908/milestones?state=closed";

const options = {
    headers: {
        Authorization: "Bearer $CI_JOB_TOKEN"
    }
};

fetch(url, options)
    .then( res => res.json() )
    .then( data => console.log(data) );

