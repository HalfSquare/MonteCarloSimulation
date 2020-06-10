## What does this Merge Request do?

Issue: #<!-- put issue number here. Make sure there is not space between the '#' and your issue number-->
<br>
<!-- Briefly describe what this MR is about. -->

## Author's checklist (required)

- [ ] Follow the [Documentation Guidelines](https://docs.gitlab.com/ee/development/documentation/) and [Style Guide](https://docs.gitlab.com/ee/development/documentation/styleguide.html).
  - [ ] Apply the ~documentation label, plus:
    - ~"development guidelines" when changing docs under `doc/development/*`, `CONTRIBUTING.md`, or `README.md`.
    - ~"development guidelines" and ~"Documentation guidelines" when changing docs under `development/documentation/*`.
    - ~"development guidelines" and ~"Description templates (.gitlab/\*)" when creating/updating issue and MR description templates.
- [ ] Assign the [designated Technical Writer](https://about.gitlab.com/handbook/engineering/ux/technical-writing/#assignments).
- [ ] Have the correct Milestone associated
- [ ] Have at least one reviewer assigned to the merge request

<!--Do not add the ~"feature", ~"frontend", ~"backend", ~"bug", or ~"database" labels if you are only updating documentation. These labels will cause the MR to be added to code verification QA issues.-->

## Co-authors
<!-- If you do not have any co-authors, delete everything in here and write 'NONE'. Otherwise keep the names and tags of your coauthors and delete all the comments-->
Co-authored-by: <!-- Author's full name followed by a space and their correspnding username tag-->
<!-- Please keep the name of your co-author: Alexander Pace, Georgia Strongman, Jacqui Dong, Justina Koh, Maz McMurray, Michael Behan-->
<!-- Please keep the tag of your co-author: @pacealex @stronggeor @dongjacq @kohjust @stronggeor @behanmich -->
Co-authored-by: <!-- Author's full name followed by a space and their correspnding username tag-->
<!-- Please keep the name of your co-author: Alexander Pace, Georgia Strongman, Jacqui Dong, Justina Koh, Maz McMurray, Michael Behan-->
<!-- Please keep the tag of your co-author: @pacealex @stronggeor @dongjacq @kohjust @stronggeor @behanmich -->

All reviewers can help ensure accuracy, clarity, completeness, and adherence to the [Documentation Guidelines](https://docs.gitlab.com/ee/development/documentation/) and [Style Guide](https://docs.gitlab.com/ee/development/documentation/styleguide.html).

**1. Primary Reviewer**

## Review checklist

All reviewers can help ensure accuracy, clarity, completeness, and adherence to the [Documentation Guidelines](https://docs.gitlab.com/ee/development/documentation/) and [Style Guide](https://docs.gitlab.com/ee/development/documentation/styleguide.html).

**1. Primary Reviewer**

* [ ] Review by a code reviewer or other selected colleague to confirm accuracy, clarity, and completeness. This can be skipped for minor fixes without substantive content changes.

**2. Maintainer**

1. [ ] Review by assigned maintainer, who can always request/require the above reviews. Maintainer's review can occur before or after a technical writer review.
1. [ ] Ensure a release milestone is set.
1. [ ] If there has not been a technical writer review, [create an issue for one using the Doc Review template](https://gitlab.com/gitlab-org/gitlab/issues/new?issuable_template=Doc%20Review).

/label ~documentation
