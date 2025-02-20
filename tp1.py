import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import itertools

# Read the CSV file
df = pd.read_csv("test_results.csv")

# Create a FacetGrid based on Threads and Operations per Thread
g = sns.FacetGrid(df, row="Threads", col="Operations per Thread", hue="Test",
                  margin_titles=True, sharey=False, height=3, aspect=1.5)

g.map(plt.plot, "Probability Add", "Avg", marker="o", ms=3)
g.set_axis_labels("Probability Add", "Avg (seconds)")
g.add_legend()

# Set the y-axis limits for each subplot
for i, ax in enumerate(g.axes.flat):
    if i<3:
        ax.set_ylim(0, 2)
    else:
        if i<6:
            ax.set_ylim(0, 5)
        else:
            ax.set_ylim(0, 15)

g.fig.subplots_adjust(hspace=10, wspace=10)

# Remove automatic legend (just in case)
if g._legend:
    g._legend.remove()

# Manually add and position the legend
g.add_legend()
g._legend.set_bbox_to_anchor((0.98, 0.15))  # Move legend outside the plot
g._legend.set_frame_on(True)  # Optional: Add a border around the legend

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

# Melt the data for Seaborn compatibility
df_diff = df_diff.melt(id_vars=["Threads", "Operations per Thread", "Probability Add", "Comparison"], 
                        value_vars=["Difference"], var_name="Test", value_name="% Difference")

# Create a FacetGrid for the differences
g = sns.FacetGrid(df_diff, row="Threads", col="Operations per Thread", hue="Comparison",
                  margin_titles=True, sharey=False, height=3, aspect=1.5)

g.map(plt.plot, "Probability Add", "% Difference", marker="o", ms=3)

# Add horizontal line at y=0
for ax in g.axes.flat:
    ax.axhline(0, color="gray", linestyle="--", linewidth=1)  # Dashed line at y=0
    #ax.set_ylim(-0.12, 0.12)

g.fig.subplots_adjust(hspace=10, wspace=10)

# Remove automatic legend (just in case)
if g._legend:
    g._legend.remove()

# Manually add and position the legend
g.add_legend()
g._legend.set_bbox_to_anchor((0.98, 0.7))  # Move legend outside the plot
g._legend.set_frame_on(True)  # Optional: Add a border around the legend

plt.tight_layout()
plt.show()