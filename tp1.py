import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import itertools

files = []
files.append(pd.read_csv("original_lists_test_results.csv"))
files.append(pd.read_csv("modified_lists_test_results.csv"))

for df in files:

    # FacetGrid based on Threads and Operations per Thread
    g = sns.FacetGrid(df, row="Threads", col="Operations per Thread", hue="Test",
                    margin_titles=True, sharey=False, height=3, aspect=1.5)

    g.map(plt.plot, "Probability Add", "Avg", marker="o", ms=3)
    g.set_axis_labels("Probability Add", "Avg (seconds)")

    g.fig.subplots_adjust(hspace=10, wspace=10)

    g.add_legend()
    g._legend.set_bbox_to_anchor((0.98, 0.5))
    g._legend.set_frame_on(True)

    plt.tight_layout()
    plt.show()

    # Pivot data so that each row represents a unique combination of "Threads", "Operations per Thread", and "Probability Add"
    df_pivot = df.pivot_table(index=["Threads", "Operations per Thread", "Probability Add"], 
                                columns="Test", values="Avg").reset_index()

    # Get all unique test cases
    test_cases = df["Test"].unique()

    # Compute differences between all pairs of test cases
    diff_list = []
    for test1, test2 in itertools.combinations(test_cases, 2):
        df_temp = df_pivot.copy()
        df_temp["Difference"] = 100 * (df_temp[test2] - df_temp[test1]) / df_temp[test1]
        df_temp["Comparison"] = f"{test2} - {test1}"  # Label for the difference
        diff_list.append(df_temp)

    # Combine all differences into a single DataFrame
    df_diff = pd.concat(diff_list, ignore_index=True)

    df_diff = df_diff.melt(id_vars=["Threads", "Operations per Thread", "Probability Add", "Comparison"], 
                            value_vars=["Difference"], var_name="Test", value_name="% Difference")

    # FacetGrid for the differences
    g = sns.FacetGrid(df_diff, row="Threads", col="Operations per Thread", hue="Comparison",
                    margin_titles=True, sharey=False, height=3, aspect=1.5)

    g.map(plt.plot, "Probability Add", "% Difference", marker="o", ms=3)

    # Add horizontal line at y=0
    for ax in g.axes.flat:
        ax.axhline(0, color="gray", linestyle="--", linewidth=1)

    g.fig.subplots_adjust(hspace=10, wspace=10)

    g.add_legend()
    g._legend.set_bbox_to_anchor((0.98, 0.3))
    g._legend.set_frame_on(True)

    plt.tight_layout()
    plt.show()

